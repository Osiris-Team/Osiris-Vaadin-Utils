package com.osiris.osiris_vaadin_utils.ui.layouts;

import com.osiris.osiris_vaadin_utils.ui.notifications.Notify;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * An abstract layout for managing images with upload, display, update, and delete functionality.
 * This class is designed to be extended with specific database operations implemented and customizations applied.
 * It supports asynchronous database operations.
 */
public abstract class ImageLayout extends HLayoutScroll {

    protected static String DEFAULT_IMAGE_WIDTH = "20vw";
    protected static String DEFAULT_IMAGE_DATA_URL_PREFIX = "data:image;base64,";

    protected boolean isAdmin;
    protected MultiFileMemoryBuffer buffer;
    protected Upload upload;
    protected Consumer<Throwable> exceptionHandler;
    protected UI ui;

    /**
     * Constructs an ImageLayout with default settings (non-admin mode).
     */
    public ImageLayout() {
        this(false);
    }

    /**
     * Constructs an ImageLayout with specified admin mode.
     *
     * @param isAdmin true if the layout should be in admin mode, false otherwise
     */
    public ImageLayout(boolean isAdmin) {
        this.ui = UI.getCurrent();
        this.isAdmin = isAdmin;
        this.buffer = new MultiFileMemoryBuffer();
        this.upload = createUpload();
        this.exceptionHandler = createExceptionHandler();

        if (isAdmin) {
            add(upload);
        }
    }

    /**
     * Creates and configures the upload component.
     * Override this method to customize the upload component.
     *
     * @return the configured Upload component
     */
    protected Upload createUpload() {
        Upload upload = new Upload(buffer);
        upload.setWidth(getImageWidth());
        upload.setI18n(createUploadI18N());
        upload.addSucceededListener(event -> handleUploadSuccess(event.getFileName()));
        return upload;
    }

    /**
     * Creates the UploadI18N object for internationalization.
     * Override this method to provide custom translations.
     *
     * @return the configured UploadI18N object
     */
    protected UploadI18N createUploadI18N() {
        UploadI18N i18n = new UploadI18N();
        i18n.setDropFiles(new UploadI18N.DropFiles().setOne("Drop Image here").setMany("Drop Images here"));
        i18n.setAddFiles(new UploadI18N.AddFiles().setOne("Add Image").setMany("Add Images"));
        return i18n;
    }

    /**
     * Creates the exception handler for this layout.
     * Override this method to provide custom exception handling.
     *
     * @return the exception handler
     */
    protected Consumer<Throwable> createExceptionHandler() {
        return (ex) -> {
            ex.printStackTrace();
            Notify.error(ex.getMessage());
        };
    }

    /**
     * Handles the successful upload of an image.
     * Override this method to customize the upload success behavior.
     *
     * @param fileName the name of the uploaded file
     */
    protected void handleUploadSuccess(String fileName) {
        if (!isAdmin) return;
        try (InputStream inputStream = buffer.getInputStream(fileName)) {
            String imageData = Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream));
            dbAddImage(imageData)
                    .thenAccept(id -> this.ui.access(() -> addImg(id, imageData)))
                    .exceptionally(ex -> {
                        this.ui.access(() -> exceptionHandler.accept(ex));
                        return null;
                    });
        } catch (IOException e) {
            exceptionHandler.accept(e);
        }
    }

    /**
     * Sets the style for an image and adds click listener for admin operations.
     * Override this method to customize the image styling and behavior.
     *
     * @param img The Image component to style
     * @param imgBase64 The base64 encoded image data
     */
    protected void setStyle(Image img, String imgBase64) {
        img.setSrc(getImageDataUrlPrefix() + imgBase64);
        img.setWidth(getImageWidth());
        img.getStyle().set("object-fit", "cover");
        if (isAdmin) {
            img.addClickListener(e -> openAdminDialog(img));
        }
    }

    /**
     * Opens the admin dialog for image operations.
     * Override this method to customize the admin dialog.
     *
     * @param img The Image component associated with the dialog
     */
    protected void openAdminDialog(Image img) {
        Dialog dialog = new Dialog();
        Upload upload = createUploadForUpdate(img, dialog);
        Button btnRemove = createRemoveButton(img, dialog);
        dialog.add(upload, btnRemove);
        dialog.open();
    }

    /**
     * Creates an upload component for updating an existing image.
     * Override this method to customize the update upload component.
     *
     * @param img The Image component to update
     * @param dialog The Dialog containing the upload component
     * @return the configured Upload component
     */
    protected Upload createUploadForUpdate(Image img, Dialog dialog) {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setI18n(createUploadI18N());
        upload.setWidthFull();
        upload.addSucceededListener(event -> handleUpdateSuccess(img, buffer, event.getFileName(), dialog));
        return upload;
    }

    /**
     * Handles the successful update of an image.
     * Override this method to customize the update success behavior.
     *
     * @param img The Image component being updated
     * @param buffer The buffer containing the new image data
     * @param fileName The name of the uploaded file
     * @param dialog The Dialog to close after successful update
     */
    protected void handleUpdateSuccess(Image img, MultiFileMemoryBuffer buffer, String fileName, Dialog dialog) {
        img.getId().ifPresent(id -> {
            try (InputStream inputStream = buffer.getInputStream(fileName)) {
                String imageData = Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream));
                int imageId = Integer.parseInt(id);
                dbUpdateImage(imageId, imageData)
                        .thenRun(() -> this.ui.access(() -> {
                            updateImg(imageId, imageData);
                            dialog.close();
                        }))
                        .exceptionally(ex -> {
                            this.ui.access(() -> exceptionHandler.accept(ex));
                            return null;
                        });
            } catch (IOException e) {
                exceptionHandler.accept(e);
            }
        });
    }

    /**
     * Creates a remove button for deleting an image.
     * Override this method to customize the remove button.
     *
     * @param img The Image component to remove
     * @param dialog The Dialog to close after successful removal
     * @return the configured Button component
     */
    protected Button createRemoveButton(Image img, Dialog dialog) {
        Button btnRemove = new Button("Remove");
        btnRemove.setWidthFull();
        btnRemove.addClickListener(e -> {
            img.getId().ifPresent(id -> {
                int imageId = Integer.parseInt(id);
                dbDeleteImage(imageId)
                        .thenRun(() -> this.ui.access(() -> {
                            deleteImg(imageId);
                            dialog.close();
                        }))
                        .exceptionally(ex -> {
                            this.ui.access(() -> exceptionHandler.accept(ex));
                            return null;
                        });
            });
        });
        return btnRemove;
    }

    /**
     * Adds a new image to the layout.
     *
     * @param id The unique identifier for the image
     * @param imageBase64 The base64 encoded image data
     */
    public void addImg(int id, String imageBase64) {
        Image img = new Image();
        setStyle(img, imageBase64);
        img.setId(String.valueOf(id));
        add(img);
    }

    /**
     * Updates an existing image in the layout.
     *
     * @param id The unique identifier of the image to update
     * @param imageBase64 The new base64 encoded image data
     */
    public void updateImg(int id, String imageBase64) {
        getChildren().forEach(component -> {
            if (component instanceof Image) {
                Image img = (Image) component;
                img.getId().ifPresent(imgId -> {
                    if (imgId.equals(String.valueOf(id))) {
                        img.setSrc(getImageDataUrlPrefix() + imageBase64);
                    }
                });
            }
        });
    }

    /**
     * Removes an image from the layout.
     *
     * @param id The unique identifier of the image to delete
     */
    public void deleteImg(int id) {
        getChildren().forEach(component -> {
            if (component instanceof Image) {
                Image img = (Image) component;
                img.getId().ifPresent(imgId -> {
                    if (imgId.equals(String.valueOf(id))) {
                        remove(img);
                    }
                });
            }
        });
    }

    /**
     * Gets the width to be used for images in this layout.
     * Override this method to customize the image width.
     *
     * @return the image width as a CSS string
     */
    protected String getImageWidth() {
        return DEFAULT_IMAGE_WIDTH;
    }

    /**
     * Gets the data URL prefix to be used for images in this layout.
     * Override this method to customize the image data URL prefix.
     *
     * @return the image data URL prefix
     */
    protected String getImageDataUrlPrefix() {
        return DEFAULT_IMAGE_DATA_URL_PREFIX;
    }

    /**
     * Abstract method to add an image to the database asynchronously.
     *
     * @param imageBase64 The base64 encoded image data
     * @return A CompletableFuture that completes with the unique identifier of the newly added image
     */
    protected abstract CompletableFuture<Integer> dbAddImage(String imageBase64);

    /**
     * Abstract method to update an image in the database asynchronously.
     *
     * @param id The unique identifier of the image to update
     * @param imageBase64 The new base64 encoded image data
     * @return A CompletableFuture that completes when the update operation is finished
     */
    protected abstract CompletableFuture<Void> dbUpdateImage(int id, String imageBase64);

    /**
     * Abstract method to delete an image from the database asynchronously.
     *
     * @param id The unique identifier of the image to delete
     * @return A CompletableFuture that completes when the delete operation is finished
     */
    protected abstract CompletableFuture<Void> dbDeleteImage(int id);
}