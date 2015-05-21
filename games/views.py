from django.views.generic.edit import CreateView
from django.core.urlresolvers import reverse_lazy, reverse
from django.shortcuts import render, render_to_response
from django.http import HttpResponseRedirect
from django import forms

from games.models import Review
from games.forms import ReviewForm, ReviewFormAlt


class CreateReview(CreateView):
    model = Review
    #form_class = ReviewForm
    template_name = "games/submit_review.html"
    success_url = reverse_lazy("submit-review-success")

def submit_review(request):
    if request.method == "POST":
        form = ReviewForm(request.POST)
        if form.is_valid():
            return HttpResponseRedirect(reverse("submit-review-success"))
    else:
        form = ReviewForm()

    return render_to_response(request, "games/submit_review.html", {"form": form})
