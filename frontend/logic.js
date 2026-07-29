async function getDogImage() {
  document.getElementById("dog-image").src = "";
  try {
    const response = await fetch("https://dog.ceo/api/breeds/image/random"); // over network
    const data = await response.json(); // Parse response as JSON
    console.log(data.message); // URL of the dog image
    document.getElementById("dog-image").src = data.message;
  } catch (error) {
    console.error("Failed to fetch dog image:", error);
  }
}

document.addEventListener("DOMContentLoaded", function () {
  const button = document.getElementById("show-dog-button");
  button.addEventListener("click", getDogImage);
});
