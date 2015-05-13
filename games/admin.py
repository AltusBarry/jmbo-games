from django.contrib import admin

from jmbo.admin import ModelBaseAdmin
from games.models import TrivialContent, Game, Reviewer


admin.site.register(TrivialContent, ModelBaseAdmin)
admin.site.register(Game, ModelBaseAdmin)
admin.site.register(Reviewer, ModelBaseAdmin)
