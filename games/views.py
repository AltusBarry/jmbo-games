from django.contrib.auth.decorators import login_required
from django.template import RequestContext
from django.views.generic.edit import CreateView
from django.core.urlresolvers import reverse_lazy, reverse
from django.shortcuts import render, render_to_response
from django.http import HttpResponseRedirect

from games.models import Review, Game
from games.forms import ReviewForm, ReviewFormAlt


class CreateReview(CreateView):
    model = Review
    #form_class = ReviewForm
    template_name = "games/submit_review.html"
    success_url = reverse_lazy("submit-review-success")

@login_required
def submit_review(request):

    if request.method == "POST":
        form = ReviewFormAlt(request.POST)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect(reverse("submit-review-success"))
    else:
        game_id = request.GET.get("game_id", "")
        game = None
        if game_id:
            game = Game.objects.get(id=game_id)
        form = ReviewFormAlt(game=game)

    extra = dict(title='Redirect', form=form)
    return render_to_response("games/submit_review.html", extra, context_instance=RequestContext(request))
