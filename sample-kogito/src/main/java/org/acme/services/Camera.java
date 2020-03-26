package org.acme.services;

import javax.enterprise.context.ApplicationScoped;

import org.acme.ImageData;

@ApplicationScoped
public class Camera {
    public ImageData takePicture() {
        return new ImageData(null);
    }
}