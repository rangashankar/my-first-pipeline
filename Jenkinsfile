pipeline {
  agent {
    docker {
      image 'maven:3.9-eclipse-temurin-17'
      args '-v $HOME/.m2:/root/.m2'
      reuseNode true
    }
  }

  options {
    timestamps()
    timeout(time: 20, unit: 'MINUTES')
    buildDiscarder(logRotator(numToKeepStr: '20'))
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
        sh 'git rev-parse --short HEAD > .git_sha || echo local > .git_sha'
      }
    }

    stage('Build & Test') {
      steps {
        sh 'mvn -B -q clean test'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
          archiveArtifacts artifacts: 'target/**/*.jar, .git_sha', onlyIfSuccessful: false
        }
      }
    }
  }

  post {
    always {
      cleanWs deleteDirs: true, notFailBuild: true
    }
  }
}

