from django.db import models
from django.core.validators import MaxValueValidator, MinValueValidator

from jmbo.models import ModelBase

from ckeditor.fields import RichTextField


class TrivialContent(ModelBase):
    """We need one model so South migrations can be initiated."""
    pass


class Game(ModelBase):

    @property
    def average_rating(self):
        return 1

    @property
    def characters(self):
        return self.character_set.all()


class Reviewer(models.Model):
    name = models.CharField(max_length=100)

    def __unicode__(self):
        return self.name


class Review(ModelBase):
    """Contains game and reviewer data as well as rating"""
    game = models.ForeignKey(Game)
    content = RichTextField()
    reviewer = models.ForeignKey(Reviewer)

    RATING_CHOICES = []
    lowest_rating = 1
    highest_rating = 5

    for r in range(lowest_rating, (highest_rating+1)):
        RATING_CHOICES.append((r, r))

    rating = models.PositiveIntegerField(choices=RATING_CHOICES, default=1)


class Character(ModelBase):
    games = models.ManyToManyField(Game)
