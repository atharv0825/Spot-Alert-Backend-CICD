pipeline {
    agent {
        docker {
            image 'maven:3.9.9-eclipse-temurin-21'
        }
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
    }
}
