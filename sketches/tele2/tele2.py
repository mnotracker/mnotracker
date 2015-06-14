#!/usr/bin/python3
# coding=utf8

import http.cookiejar
import urllib
import urllib.parse
import urllib.request
import sys
import re
import os
from bs4 import BeautifulSoup

from creds_dont_commit import *

SIMULATION = True

# utils {

cookies = http.cookiejar.MozillaCookieJar()
opener = urllib.request.build_opener(
    urllib.request.HTTPCookieProcessor(cookies),
    urllib.request.HTTPRedirectHandler,
    #urllib.request.FancyURLopener(),
)
opener.addheaders = [('User-agent', 'Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30')]

def read_file(filename):
    return open(filename).read()

def maybe_strip(text):
    try: return text.strip()
    except: return ''

def p(url, post_params=None):
    op = opener.open(url) if post_params is None else opener.open(url, urllib.parse.urlencode(post_params).encode())
    text = op.read().decode()
    print()
    print('"%s"' % url)
    print(text, op.getheaders(), op.status)
    return text

# utils }

def authorize(phone, password):
    if SIMULATION:
        return read_file('01_on_login_success.html')
    else:
        p('https://login.tele2.ru')
        return p('https://login.tele2.ru:443/ssotele2/wap/auth/submitLoginAndPassword', {'pNumber': phone, 'password': password})

#def print_services_1(on_login_success_page):
#    soap = BeautifulSoup(on_login_success_page)
#    a = soap.findAll('a')
#    url_text = map(lambda t: (t['href'], maybe_strip(t.string)), a)
#    url_text = filter(lambda t: t[1].find('Личный кабинет Теле2') != -1, url_text)
#    uri = tuple(url_text)[0][0]
#    url = 'https://login.tele2.ru:443/' + uri
#    return p(url)

def print_services_2():
    if SIMULATION:
        return read_file('02_on_services_success_page.html')
    else:
        return p('https://my.tele2.ru/services')

on_login_success_page = authorize()
on_services_success_page = print_services_2()
