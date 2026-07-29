async function getDogImage() {
  const dogImage = document.getElementById("dog-image") as HTMLImageElement;
  dogImage.src = "";

  try {
    const response = await fetch("https://dog.ceo/api/breeds/image/random"); // over network
    const data = await response.json(); // Parse response as JSON
    console.log(data.message); // URL of the dog image
    dogImage.src = data.message;
  } catch (error) {
    console.error("Failed to fetch dog image:", error);
  }
}

document.addEventListener("DOMContentLoaded", function () {
  const button = document.getElementById("show-dog-button");
  if (button) {
    button.addEventListener("click", getDogImage);
  }
});

export { getDogImage };
