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
    game = forms.ModelChoiceField(queryset=Game.objects.all())
    title = forms.CharField()
    content = forms.CharField()
    reviewer = forms.ModelChoiceField(queryset=Reviewer.objects.all(), widget=HiddenInput)

    def __init__(self, *args, **kwargs):
        print kwargs
        game = None
        if kwargs.has_key("game"):
            game = kwargs.pop("game")
        super(ReviewFormAlt, self).__init__(*args, **kwargs)
        # Set initial Values for fields
        if not self.is_bound:
            self.fields["game"].initial = game
            self.fields["reviewer"].initial = Reviewer.objects.all()[0]
            self.fields["title"].initial = game.title + "-Review"

    def save(self):

        # TODO merely put the whole cleaned data in, Review should be able to handle input
        return Review.objects.create(**self.cleaned_data)