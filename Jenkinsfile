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

        stage('Verify Tools') {
            steps {
                sh '''
                    java -version
                    mvn -version
                    docker --version
                '''
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
                // ❌ this line failed earlier because docker was not available
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
