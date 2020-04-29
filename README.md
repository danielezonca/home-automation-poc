Home Automation demo
===================

This repo contains a sample project based on [Kogito](https://kogito.kie.org/) and [Camel-K](https://camel.apache.org/camel-k/latest/index.html).

## Folders content

- `camel-k`: this folder contains a set of Camel-K routes used to integrate with different services
- `knownImages`: set of images used as knowledge base. All known member of the family should provide one or two pictures
- `sample-kogito`: Kogito application that implement process orchestration and decision logic
- `testImages`: folder of pictures used to mock a camera service. Camel-K `take-picture` route emulate a camera just picking randomly one of the file from that folder

## How to run

This project can run with OpenShift 4 with or without Serverless (knative). It should work in the same way on K8s but it has not been tested

### Deploy face recognition service

In this demo we use a face recognition service based on deep learning ([dlib](http://dlib.net/) library) wrapped in a Python web service to have a REST API.

You can find the code in [this repo](https://github.com/danielezonca/face_recognition).

It is enough to build it

`docker build -t tag_name:1.0 .`

Push it to your registry

`docker push tag_name:1.0`

Deploy it with a YAML like this (`oc apply -f file.yaml`)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: face-recognition
  namespace: cloud-sweet-cloud
spec:
  selector:
    matchLabels:
      app: face-recognition
  replicas: 1
  template:
    metadata:
      labels:
        app: face-recognition
    spec:
      containers:
        - name: face-recognition
          image: yourrepo/your-image
          ports:
            - containerPort: 8080
```

Finally expose it

`oc expose deployment face-recognition`

`oc expose service face-recognition`

### Deploy Camel-K services

Use [Camel-K installation guide](https://github.com/apache/camel-k#installation) to install `kamel` CLI.

After that you can just run it with each of resources to deploy them

### Deploy sample-kogito

This module is a Quarkus application and you need to decide if you want to run it in JVM mode or in native mode.

Just compile it with `mvn package` for JVM mode or with `mvn package -Pnative` for native mode (you need GraalVM 20 for this).

After than build your container image with the Dockerfile you need:
- `Dockerfile.jvm` for JVM mode
- `Dockerfile.native` for native mode

Then push your image to your registry and finally deploy it on OpenShift Serverless with this YAML file

```yaml
apiVersion: serving.knative.dev/v1
kind: Service
metadata:
  name: sample-kogito
  namespace: cloud-sweet-cloud
spec:
  template:
    spec:
      containers:
        - name: sample-kogito
          image: quay.io/danielezonca/home-automation-poc:1.0
          ports:
            - containerPort: 8080
          env:
            - name: CAMERA_HOST
              value: "take-picture"
            - name: CAMERA_PORT
              value: "80"
            - name: CAMERA_SSL
              value: "false"
            - name: CAMERA_ENDPOINT
              value: "/"
            - name: PLAYER_HOST
              value: "notifier-playlist"
            - name: PLAYER_PORT
              value: "80"
            - name: PLAYER_SSL
              value: "false"
            - name: PLAYER_ENDPOINT
              value: "/"
            - name: RECOGNITION_SERVICE_HOST
              value: "face-recognition"
            - name: RECOGNITION_SERVICE_PORT
              value: "80"
            - name: RECOGNITION_SERVICE_SSL
              value: "false"
            - name: RECOGNITION_SERVICE_ENDPOINT
              value: "/"
            - name: TELEGRAM_HOST
              value: "notifier"
            - name: TELEGRAM_PORT
              value: "80"
            - name: TELEGRAM_SSL
              value: "false"
            - name: TELEGRAM_ENDPOINT
              value: "/"
``` 

If you want to deploy without knative just change `apiVersion` to `apps/v1` and `kind` to `Deployment`. 

NOTE: service and route are created automatically using knative while you still need to create them if you are using `Deployment`