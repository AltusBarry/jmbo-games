from django.conf.urls import patterns, url, include

from foundry.urls import *


urlpatterns += patterns('',
    (r'^', include('games.urls')),
)
