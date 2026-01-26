pipeline {
    agent any

    tools {
        maven 'Maven-3.8.7'
        jdk 'jdk17'
    }

    environment {
        IMAGE_NAME     = 'spotalert-backend:latest'
        CONTAINER_NAME = 'spotalert-backend'
        ENV_FILE       = '/opt/spotalert/.env'
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

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Stop Old Container') {
            steps {
                sh '''
                    docker stop $CONTAINER_NAME || true
                    docker rm $CONTAINER_NAME || true
                '''
            }
        }

        stage('Run New Container') {
            steps {
                sh '''
                    docker run -d \
                      --name $CONTAINER_NAME \
                      --env-file $ENV_FILE \
                      --add-host=host.docker.internal:host-gateway \
                      -p 8081:8081 \
                      $IMAGE_NAME
                '''
            }
        }
    }
}
