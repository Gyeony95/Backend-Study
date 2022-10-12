from django.shortcuts import render
from rest_framework import viewsets

from scrap_app.models import Test
from scrap_app.serializers import TestSerializer


# Create your views here.
class TestViewSet(viewsets.ModelViewSet):
    queryset = Test.objects.all()
    serializer_class = TestSerializer
