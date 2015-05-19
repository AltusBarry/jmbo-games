from django.contrib import admin
from django import forms

from jmbo.admin import ModelBaseAdmin

from games.models import TrivialContent, Game, Review, Reviewer, Character


class ReviewAdminForm(forms.ModelForm):
    class Meta:
        model = Review
        widgets =   {
            'rating' :forms. RadioSelect
                    }


class ReviewAdmin(ModelBaseAdmin):
    form = ReviewAdminForm


admin.site.register(TrivialContent, ModelBaseAdmin)
admin.site.register(Game, ModelBaseAdmin)
admin.site.register(Reviewer, admin.ModelAdmin)
admin.site.register(Review, ReviewAdmin)
admin.site.register(Character, ModelBaseAdmin)
