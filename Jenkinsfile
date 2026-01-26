pipeline {
    agent any

    tools {
            maven 'Maven-3.8.7'
            jdk 'jdk17'
    }

    environment {
            IMAGE_NAME = 'spotalert-backend:latest'
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

        stage('Stop Old Container') {
                    steps {
                        sh '''
                        docker stop $CONTAINER_NAME || true
                        docker rm $CONTAINER_NAME || true
                        '''
                    }
                }
    }
}
