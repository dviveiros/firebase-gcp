firebasegcp-twitter-connector
=============================

//All credits to Fabio Uechi (fuechi@ciandt.com) who created most of the code for Smart Canvas

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


docker run -it --rm -e TWITTER_CONSUMER_KEY=$TWITTER_CONSUMER_KEY -e TWITTER_CONSUMER_SECRET=$TWITTER_CONSUMER_SECRET -e TWITTER_ACCESS_TOKEN=$TWITTER_ACCESS_TOKEN -e TWITTER_ACCESS_TOKEN_SECRET=$TWITTER_ACCESS_TOKEN_SECRET

```
