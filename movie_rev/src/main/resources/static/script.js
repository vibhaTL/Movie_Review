const apiUrl = "/api/reviews"; // Spring Boot API

// Fetch and display all reviews
function fetchReviews() {
    fetch(apiUrl)
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById("reviewList");
            list.innerHTML = "";
            data.forEach(review => {
                const li = document.createElement("li");
                li.innerHTML = `<b>${review.movieTitle}</b> by ${review.reviewer} - Rating: ${review.rating}<br>${review.comment || ""}`;
                list.appendChild(li);
            });
        })
        .catch(err => console.error("Error fetching reviews:", err));
}

// Add review
document.getElementById("reviewForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const review = {
        movieTitle: document.getElementById("movieTitle").value,
        reviewer: document.getElementById("reviewer").value,
        rating: parseInt(document.getElementById("rating").value),
        comment: document.getElementById("comment").value
    };

    fetch(apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(review)
    })
    .then(res => res.json())
    .then(data => {
        alert("Review added!");
        document.getElementById("reviewForm").reset();
        fetchReviews();
    })
    .catch(err => console.error("Error adding review:", err));
});

// Initial fetch
fetchReviews();
