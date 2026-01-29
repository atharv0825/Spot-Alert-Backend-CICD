pipeline {
    agent any

    environment {
        IMAGE_NAME     = 'spotalert-backend:latest'
        CONTAINER_NAME = 'spotalert-backend'
        ENV_FILE       = '/opt/spotalert/.env'
        SPRING_PROFILE = 'prod'
        DOCKER_NETWORK = 'spotalert-net'
    }

    stages {

        stage('Verify Docker') {
            steps {
                sh 'docker --version'
            }
        }

        stage('Build Image (Dockerized Maven)') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
                // Maven runs INSIDE Docker → no host RAM pressure
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    docker rm -f $CONTAINER_NAME || true

                    docker run -d \
                      --name $CONTAINER_NAME \
                      --network $DOCKER_NETWORK \
                      --env-file $ENV_FILE \
                      -e SPRING_PROFILES_ACTIVE=$SPRING_PROFILE \
                      -p 127.0.0.1:8081:8080 \
                      --restart unless-stopped \
                      $IMAGE_NAME
                '''
            }
        }
    }
}
