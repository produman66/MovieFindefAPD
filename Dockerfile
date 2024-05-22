# Используем базовый образ с OpenJDK 11
FROM openjdk:11-jdk

# Устанавливаем необходимые инструменты
RUN apt-get update && \
    apt-get install -y wget unzip && \
    apt-get clean

# Устанавливаем Android SDK
ENV ANDROID_SDK_URL="https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip"
ENV ANDROID_SDK_ROOT="/sdk"
RUN mkdir -p ${ANDROID_SDK_ROOT} && \
    wget -q ${ANDROID_SDK_URL} -O /tmp/tools.zip && \
    unzip /tmp/tools.zip -d ${ANDROID_SDK_ROOT}/cmdline-tools && \
    mv ${ANDROID_SDK_ROOT}/cmdline-tools/cmdline-tools ${ANDROID_SDK_ROOT}/cmdline-tools/latest

# Устанавливаем необходимые компоненты Android SDK
RUN yes | ${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager --sdk_root=${ANDROID_SDK_ROOT} --licenses
RUN ${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager --sdk_root=${ANDROID_SDK_ROOT} "platform-tools" "platforms;android-31" "build-tools;31.0.0"

# Устанавливаем Gradle
ENV GRADLE_VERSION=8.2
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp && \
    unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip && \
    rm /tmp/gradle-${GRADLE_VERSION}-bin.zip
ENV GRADLE_HOME=/opt/gradle/gradle-${GRADLE_VERSION}
ENV PATH=${GRADLE_HOME}/bin:${PATH}

# Устанавливаем Kotlin
ENV KOTLIN_VERSION=1.6.21
RUN wget -q -O /usr/local/bin/kotlinc https://github.com/JetBrains/kotlin/releases/download/v${KOTLIN_VERSION}/kotlin-compiler-${KOTLIN_VERSION}.zip && \
    chmod +x /usr/local/bin/kotlinc

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем проект в контейнер
COPY . .

# Собираем проект
RUN gradle build

# Команда по умолчанию
CMD ["gradle", "assemble"]