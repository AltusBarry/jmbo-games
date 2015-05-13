from django.db import models
from jmbo.models import ModelBase
from ckeditor.fields import RichTextField


class TrivialContent(ModelBase):
    """We need one model so South migrations can be initiated."""
    pass


class Game(ModelBase):
    reviewer = models.ForeignKey("Reviewer")
    """ 
    review_text = RichTextField( blank=True, null=True, )
    """
    review_text = "TEXT"


class Reviewer(ModelBase):
    reviewer_name = models.CharField(max_length=100)

    def __unicode__(self):
        return self.reviewer_name

    @property
    def games(self):
        return self.game_set.all().order_by("title")
