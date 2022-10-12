from rest_framework import serializers

from scrap_app.models import Test


class TestSerializer(serializers.ModelSerializer):
    class Meta:
        model = Test
        fields = ('test',)