pipeline {
    agent any


    tools {
        maven 'maven-3'
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

    }
}
