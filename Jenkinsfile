pipeline {
    agent any

    tools {
            maven 'Maven-3.8.7'
            jdk 'jdk17'
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
