@echo off
echo Starting Gradle build...
call gradlew -g I:\.gradle assembleDebug
pause