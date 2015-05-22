from django.conf.urls import patterns, url, include
from django.views.generic.base import TemplateView

from games.views import CreateReview, submit_review
from games.forms import ReviewFormAlt

urlpatterns = patterns(

    "",
    url(r"^submit-review/$", CreateReview.as_view(), name="submit-review"),
    url(r"^review-alt-submit/$", submit_review,
        name="review-alt-submit"),
    url(r"^submit-review-success/$",
        TemplateView.as_view(template_name="games/submit_review_success.html"),
        name="submit-review-success")

)
