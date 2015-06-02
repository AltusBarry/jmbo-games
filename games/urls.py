from django.conf.urls import patterns, url
from django.views.generic.base import TemplateView

from jmbo.urls import v1_api

from games.views import CreateReview, submit_review
from games.api import GameResource

v1_api.register(GameResource())

urlpatterns = patterns(

    "",
    url(r"^submit-review/$", CreateReview.as_view(), name="submit-review"),
    url(r"^review-alt-submit/$", submit_review,
        name="review-alt-submit"),
    url(r"^submit-review-success/$",
        TemplateView.as_view(template_name="games/submit_review_success.html"),
        name="submit-review-success")

)
