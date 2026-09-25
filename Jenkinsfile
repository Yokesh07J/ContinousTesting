pipeline {

    agent any

    tools {
        maven 'Maven-3.9.6'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Yokesh07J/ContinousTesting.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Automated Testing') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Generate Test Report') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {

        success {
            echo 'All automated tests passed successfully!'
        }

        failure {
            echo 'Automated testing failed. Check the test report.'
        }

        always {
            echo 'Continuous Testing completed.'
        }
    }
}