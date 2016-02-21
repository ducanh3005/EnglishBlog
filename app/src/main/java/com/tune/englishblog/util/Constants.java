package com.tune.englishblog.util;

/**
 * Created by TungNT on 19/02/2016.
 */
public interface Constants {
    public static final String MIME_TYPE = "text/html";
    public static final String ENCODING = "UTF-8";

    public static final String HTML_HEAD = "<head><style type='text/css'> "
            + "body {max-width: 100%; margin: 0.3cm; font-family: sans-serif-light; color: #000000; background-color: #f6f6f6; line-height: 150%} "
            + "* {max-width: 100%; word-break: break-word}"
            + "h1, h2 {font-weight: normal; line-height: 130%} "
            + "h1 {font-size: 170%; margin-bottom: 0.1em} "
            + "h2 {font-size: 140%} "
            + "a {color: #0099CC}"
            + "h1 a {color: inherit; text-decoration: none}"
            + "img {height: auto} "
            + "pre {white-space: pre-wrap;} "
            + ".blockquote {border-left: thick solid #a6a6a6; background-color: #e6e6e6; margin: 0.5em 0 0.5em 0em; padding: 0.5em} "
            + "p {margin: 0.8em 0 0.8em 0} "
            + "p.subtitle {color: #666666; border-top:1px solid #ddd; border-bottom:1px solid #ddd; padding-top:2px; padding-bottom:2px; font-weight:800 } "
            + "ul, ol {margin: 0 0 0.8em 0.6em; padding: 0 0 0 1em} "
            + "ul li, ol li {margin: 0 0 0.8em 0; padding: 0} "
            + "</style><meta name='viewport' content='width=device-width'/></head><body>";
    public static final String HTML_BODY_END = "</body>";
    public static final String TITLE_START = "<h1>";
    public static final String TITLE_END = "</h1>";
}
