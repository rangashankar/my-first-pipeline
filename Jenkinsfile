pipeline {
  agent {
    docker {
      image 'maven:3.9-eclipse-temurin-17'
      // cache your maven repo to speed builds; optional
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
        // Prove mvn exists (prevents mysterious 127)
        sh 'mvn -v'
        sh 'mvn -B -q clean test'
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
          archiveArtifacts artifacts: 'target/**/*.jar, .git_sha', onlyIfSuccessful: false
          // ✅ Do cleanup at STAGE level where a workspace definitely exists
          cleanWs deleteDirs: true, notFailBuild: true
        }
      }
    }
  }

  post {
    always {
      // If you ALSO want pipeline-level cleanup, wrap it in a node so a workspace exists
      node(env.NODE_NAME ?: 'built-in') {
        cleanWs deleteDirs: true, notFailBuild: true
      }
    }
  }
}

