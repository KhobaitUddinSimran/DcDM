# Deepfake Call Detector

Deepfake Call Detector is an Android application designed to detect and alert users about potential deepfake audio during phone calls.

## Features

- Detects incoming and outgoing calls.
- Records audio during calls.
- Analyzes recorded audio for deepfake detection.
- Displays detected audio files in a dashboard.
- Provides a user-friendly interface with navigation drawer and tabs.

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/your-repository.git
    ```
2. Open the project in Android Studio.
3. Build the project to download dependencies and set up the environment.

## Usage

1. Run the application on an Android device or emulator.
2. Grant necessary permissions for call detection and audio recording.
3. Use the dashboard to view detected audio files and their statuses.

## Project Structure

- `app/src/main/java/com/example/deepfakecalldetector/CallReceiver.kt`: Handles call state changes and starts/stops the audio recording service.
- `app/src/main/java/com/example/deepfakecalldetector/DashboardScreen.kt`: Composable function for the dashboard screen displaying detected audio files.
- `app/src/main/java/com/example/deepfakecalldetector/audio/DetectedAudio.kt`: Data class representing detected audio files.
- `app/src/main/java/com/example/deepfakecalldetector/audio/sampleAudioList.kt`: Sample data for detected audio files.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
