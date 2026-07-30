let dogImage = null;
dogImage = document.getElementById("dog-image");
const button = document.getElementById("show-dog-button");
export async function getDogImage() {
    try {
        const response = await fetch("https://dog.ceo/api/breeds/image/random"); // over network
        const data = await response.json(); // Parse response as JSON
        if (dogImage) {
            dogImage.src = data.message;
        }
    }
    catch (error) {
        console.error("Failed to fetch dog image:", error);
    }
}
document.addEventListener("DOMContentLoaded", function () {
    if (button) {
        button.addEventListener("click", getDogImage);
    }
});
