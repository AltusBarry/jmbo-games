from django.conf.urls import patterns, url, include
from games.views import ReviewCreate

urlpatterns = patterns(

    "",
    url(r'^submit-review/$', ReviewCreate.as_view(), name="submit-review")

)
