from django.contrib import admin

from jmbo.admin import ModelBaseAdmin

from games.models import TrivialContent, Game, Review, Reviewer


admin.site.register(TrivialContent, ModelBaseAdmin)
admin.site.register(Game, ModelBaseAdmin)
admin.site.register(Reviewer, models.ModelAdmin)
admin.site.register(Review, ModelBaseAdmin)
"""
class ReviewerAdmin(admin.ModelAdmin)
    fieldsets = [
        (None,      {'fields' : ['name']})
                ]
"""
