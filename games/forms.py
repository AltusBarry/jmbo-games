from django.forms import ModelForm
from django import forms
from django.forms.widgets import HiddenInput

from games.models import Review, Reviewer, Game


class ReviewForm(ModelForm):
    class Meta:
        model = Review
        fields = ("game", "content", "reviewer")
        widgets = {"reviewer": HiddenInput}

    def clean_reviewer(self):
        reviewer = Reviewer.objects.all()[0]
        return reviewer

    def __init__(self, *args, **kwargs):
        super(ReviewForm, self).__init__(*args, **kwargs)
        self.fields["reviewer"].required = False


class ReviewFormAlt(forms.Form):
    games = forms.ModelChoiceField(queryset=Game.objects.all())
    title = "-Review"
    content = forms.CharField()
    reviewer = forms.ModelChoiceField(queryset=Reviewer.objects.all(), widget=HiddenInput)
    #forms.Form.fields["reviewer"].widget = forms.HiddenInput()

    def __init__(self, *args, **kwargs):
        print kwargs
        super(ReviewFormAlt, self).__init__()
