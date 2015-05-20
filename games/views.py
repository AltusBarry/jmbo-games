from django.views.generic.edit import CreateView
from games.models import Review


class ReviewCreate(CreateView):
    model = Review
    template_name = "games/submit_review.html"
    fields = ["title", "game", "content", "reviewer"]
