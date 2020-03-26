package org.acme.services;

import javax.enterprise.context.ApplicationScoped;

import org.acme.ImageData;
import org.acme.User;


@ApplicationScoped
public class RecognitionService {
    public User recognize(ImageData imageData) {
        return new User("evacchi");
    }
}