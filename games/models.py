from django.db import models
from jmbo.models import ModelBase
from ckeditor.fields import RichTextField


class TrivialContent(ModelBase):
    """We need one model so South migrations can be initiated."""
    pass


class Game(ModelBase):

    @property
    def average_rating(self):
        return 1


class Reviewer(models.Model):
    name = models.CharField(max_length=100)

    def __unicode__(self):
        return self.name


class Review(ModelBase):
    """Contains game and reviewer data as well as rating"""
    game = models.ForeignKey(Game)
    content = RichTextField()
    reviewer = models.ForeignKey(Reviewer)
    rating = models.PositiveIntegerField(min_value=1, max_value=5, default=1)

    @property
    def games(self):
        return self.game_set.all().order_by("title")


class Character(ModelBase):
    games = models.ManyToManyField(Game)
