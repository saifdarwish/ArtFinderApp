# Art Finder App

### Overview

The Art Finder App is designed to help users discover and explore various forms of art. The app leverages a clean and intuitive interface to provide users with easy access to a vast collection of artworks, including paintings, sculptures, and digital art.

### Implementation

**Framework:** [Android]

**API Version:** [27]

**Device(s) Tested On:**
- [Pixel 6]

### Features

- **Art Discovery:** Browse a curated selection of artworks from various categories and periods.
- **Detailed Art Information:** View detailed information about each artwork, including the artist, creation date, and historical context.
- **Search Functionality:** Easily find specific artworks or artists using the search features.

### External Libraries and Frameworks

The Simple Art Finder App utilizes several external libraries and frameworks to enhance functionality and ensure a smooth user experience:

- **`libs.jsoup`:** Used to transform HTML strings into normal strings, ensuring clean and readable text descriptions for artworks.
- **`libs.listenablefuture`:** Suppresses the error "com.google.guava:guava:23.0," providing a more stable and error-free environment.
- **`libs.compiler`:** Essential for concurrent futures, allowing for efficient handling of asynchronous tasks.
- **`libs.play.services.wallet`:** Prevents app crashes related to Google Play services, ensuring a stable and reliable application.

### Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/saifdarwish/ArtFinderApp.git
    ```
2. **Open the project in Android Studio.**
3. **Build and run the app on your device or emulator.**

### Usage

1. **Launch the Art Finder App on your Android device.**
2. **Browse through the filters to discover new and interesting pieces.**
3. **Use the search bar to find specific artworks or artists.**
4. **Tap on any artwork to view detailed information.**

### Contributing

We welcome contributions to the Art Finder App! If you would like to contribute, please follow these steps:

1. **Fork the repository.**
2. **Create a new branch:**
    ```bash
    git checkout -b feature/YourFeatureName
    ```
3. **Make your changes and commit them:**
    ```bash
    git commit -m 'Add some feature'
    ```
4. **Push to the branch:**
    ```bash
    git push origin feature/YourFeatureName
    ```
5. **Submit a pull request.**

### License

This project is free to use.

### Acknowledgements

We would like to thank the open-source community for providing the libraries and frameworks that made this project possible. Special thanks to the Chicago Art Institute for providing the free API, which the whole app is based on.

For any questions or concerns, please open an issue or contact us at [saif.kheri@yahoo.com].
