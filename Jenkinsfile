pipeline {
    agent {
        docker {
            image 'maven:3.9.9-eclipse-temurin-21'
        }
    }

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
    }
}
