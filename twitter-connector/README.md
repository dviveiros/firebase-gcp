firebasegcp-twitter-connector
=============================

All credits to Fabio Uechi (fuechi@ciandt.com) who created most of the code for Smart Canvas

Twitter Connector for Firebase GCP Demo


#### Building (requires Gradle and Docker)

```bash

gradle clean installDist && docker build -t firebasegcp/twitter-connector .

```


#### Running within a docker container

```bash

# Create a twitter app and fill in the variables below
export TWITTER_CONSUMER_KEY=FILL_ME_IN
export TWITTER_CONSUMER_SECRET=FILL_ME_IN
export TWITTER_ACCESS_TOKEN=FILL_ME_IN
export TWITTER_ACCESS_TOKEN_SECRET=FILL_ME_IN
export TWITTER_HASHTAG=FILL_ME_IN

docker run -it --rm -e TWITTER_CONSUMER_KEY=$TWITTER_CONSUMER_KEY -e TWITTER_CONSUMER_SECRET=$TWITTER_CONSUMER_SECRET -e TWITTER_ACCESS_TOKEN=$TWITTER_ACCESS_TOKEN -e TWITTER_ACCESS_TOKEN_SECRET=$TWITTER_ACCESS_TOKEN_SECRET

```

#### Running on GCE

- Create your GCE VM
- Check if it is created: gcloud compute instances list
- Connect to your project. Example: gcloud compute --project "s10-viveiros" ssh --zone "us-central1-c" "firebase-gce"
- Install docker:
    - sudo yum update
    - curl -sSL https://get.docker.com/ | sh
    - sudo service docker start
    - sudo docker run hello-world (test docker installation)
- Create the environment variables:
    export TWITTER_CONSUMER_KEY=...
    export TWITTER_CONSUMER_SECRET=...
    export TWITTER_ACCESS_TOKEN=...
    export TWITTER_ACCESS_TOKEN_SECRET=...
    export TWITTER_HASHTAG=...
- Run the container: sudo docker run -it --rm -e TWITTER_CONSUMER_KEY=$TWITTER_CONSUMER_KEY -e TWITTER_CONSUMER_SECRET=$TWITTER_CONSUMER_SECRET -e TWITTER_ACCESS_TOKEN=$TWITTER_ACCESS_TOKEN -e TWITTER_ACCESS_TOKEN_SECRET=$TWITTER_ACCESS_TOKEN_SECRET -e TWITTER_HASHTAG=$TWITTER_HASHTAG dviveiros/firebasegcp-twitter-connector

#### Running on GKE

- Create your GKE cluster
- Check if it is created: gcloud compute instances list
- Generate credentials: gcloud beta container get-credentials --cluster=firebase-gke --zone=us-central1-c
- Test access: kubectl get pods (nothing should appears here)
- Create your pod: kubectl create -f ./firebase-pod.yaml --validate
    Where firebase-pod.yaml should be something like:
        apiVersion: v1
        kind: Pod
        metadata:
          name: firebase-node
        spec:  # specification of the pod’s contents
          restartPolicy: Never
          containers:
          - name: firebase-twitter-connector
            image: "gcr.io/s10-viveiros/firebase-twitter-connector"
            env:
              - name: TWITTER_CONSUMER_KEY
                value: FILL_ME_IN
              - name: TWITTER_CONSUMER_SECRET
                value: FILL_ME_IN
              - name: TWITTER_ACCESS_TOKEN
                value: FILL_ME_IN
              - name: TWITTER_ACCESS_TOKEN_SECRET
                value: FILL_ME_IN
              - name: TWITTER_HASHTAG
                value: FILL_ME_IN

- Check if your pod is working: kubectl get pods
- Delete the pod after you are done: kubectl delete pod firebase-node


