package ru.noties.markwon;

import android.content.Context;
import android.support.annotation.NonNull;

import ru.noties.markwon.html.MarkwonHtmlParser;
import ru.noties.markwon.html.MarkwonHtmlRenderer;
import ru.noties.markwon.image.AsyncDrawableLoader;
import ru.noties.markwon.image.AsyncDrawableLoaderNoOp;
import ru.noties.markwon.image.ImageSizeResolver;
import ru.noties.markwon.image.ImageSizeResolverDef;
import ru.noties.markwon.spans.LinkSpan;
import ru.noties.markwon.spans.MarkwonTheme;

/**
 * since 3.0.0 renamed `SpannableConfiguration` -&gt; `MarkwonConfiguration`
 */
@SuppressWarnings("WeakerAccess")
public class MarkwonConfiguration {

    // creates default configuration
    @NonNull
    public static MarkwonConfiguration create(@NonNull Context context) {
        return new Builder(context).build(MarkwonTheme.create(context), new AsyncDrawableLoaderNoOp());
    }

    @NonNull
    public static Builder builder(@NonNull Context context) {
        return new Builder(context);
    }


    private final MarkwonTheme theme;
    private final AsyncDrawableLoader asyncDrawableLoader;
    private final SyntaxHighlight syntaxHighlight;
    private final LinkSpan.Resolver linkResolver;
    private final UrlProcessor urlProcessor;
    private final ImageSizeResolver imageSizeResolver;
    private final SpannableFactory factory; // @since 1.1.0
    private final MarkwonHtmlParser htmlParser; // @since 2.0.0
    private final MarkwonHtmlRenderer htmlRenderer; // @since 2.0.0

    private MarkwonConfiguration(@NonNull Builder builder) {
        this.theme = builder.theme;
        this.asyncDrawableLoader = builder.asyncDrawableLoader;
        this.syntaxHighlight = builder.syntaxHighlight;
        this.linkResolver = builder.linkResolver;
        this.urlProcessor = builder.urlProcessor;
        this.imageSizeResolver = builder.imageSizeResolver;
        this.factory = builder.factory;
        this.htmlParser = builder.htmlParser;
        this.htmlRenderer = builder.htmlRenderer;
    }

    /**
     * Returns a new builder based on this configuration
     */
    @NonNull
    public Builder newBuilder(@NonNull Context context) {
        return new Builder(context, this);
    }

    @NonNull
    public MarkwonTheme theme() {
        return theme;
    }

    @NonNull
    public AsyncDrawableLoader asyncDrawableLoader() {
        return asyncDrawableLoader;
    }

    @NonNull
    public SyntaxHighlight syntaxHighlight() {
        return syntaxHighlight;
    }

    @NonNull
    public LinkSpan.Resolver linkResolver() {
        return linkResolver;
    }

    @NonNull
    public UrlProcessor urlProcessor() {
        return urlProcessor;
    }

    @NonNull
    public ImageSizeResolver imageSizeResolver() {
        return imageSizeResolver;
    }

    @NonNull
    public SpannableFactory factory() {
        return factory;
    }

    /**
     * @since 2.0.0
     */
    @NonNull
    public MarkwonHtmlParser htmlParser() {
        return htmlParser;
    }

    /**
     * @since 2.0.0
     */
    @NonNull
    public MarkwonHtmlRenderer htmlRenderer() {
        return htmlRenderer;
    }

    @SuppressWarnings("unused")
    public static class Builder {

        private final Context context;

        private MarkwonTheme theme;
        private AsyncDrawableLoader asyncDrawableLoader;
        private SyntaxHighlight syntaxHighlight;
        private LinkSpan.Resolver linkResolver;
        private UrlProcessor urlProcessor;
        private ImageSizeResolver imageSizeResolver;
        private SpannableFactory factory; // @since 1.1.0
        private MarkwonHtmlParser htmlParser; // @since 2.0.0
        private MarkwonHtmlRenderer htmlRenderer; // @since 2.0.0

        Builder(@NonNull Context context) {
            this.context = context;
        }

        Builder(@NonNull Context context, @NonNull MarkwonConfiguration configuration) {
            this(context);
            this.theme = configuration.theme;
            this.asyncDrawableLoader = configuration.asyncDrawableLoader;
            this.syntaxHighlight = configuration.syntaxHighlight;
            this.linkResolver = configuration.linkResolver;
            this.urlProcessor = configuration.urlProcessor;
            this.imageSizeResolver = configuration.imageSizeResolver;
            this.factory = configuration.factory;
            this.htmlParser = configuration.htmlParser;
            this.htmlRenderer = configuration.htmlRenderer;
        }

        @NonNull
        public Builder syntaxHighlight(@NonNull SyntaxHighlight syntaxHighlight) {
            this.syntaxHighlight = syntaxHighlight;
            return this;
        }

        @NonNull
        public Builder linkResolver(@NonNull LinkSpan.Resolver linkResolver) {
            this.linkResolver = linkResolver;
            return this;
        }

        @NonNull
        public Builder urlProcessor(@NonNull UrlProcessor urlProcessor) {
            this.urlProcessor = urlProcessor;
            return this;
        }

        /**
         * @since 1.0.1
         */
        @NonNull
        public Builder imageSizeResolver(@NonNull ImageSizeResolver imageSizeResolver) {
            this.imageSizeResolver = imageSizeResolver;
            return this;
        }

        /**
         * @since 1.1.0
         */
        @NonNull
        public Builder factory(@NonNull SpannableFactory factory) {
            this.factory = factory;
            return this;
        }

        /**
         * @since 2.0.0
         */
        @NonNull
        public Builder htmlParser(@NonNull MarkwonHtmlParser htmlParser) {
            this.htmlParser = htmlParser;
            return this;
        }

        /**
         * @since 2.0.0
         */
        @NonNull
        public Builder htmlRenderer(@NonNull MarkwonHtmlRenderer htmlRenderer) {
            this.htmlRenderer = htmlRenderer;
            return this;
        }

        @NonNull
        public MarkwonConfiguration build(@NonNull MarkwonTheme theme, @NonNull AsyncDrawableLoader asyncDrawableLoader) {

            this.theme = theme;
            this.asyncDrawableLoader = asyncDrawableLoader;

            if (syntaxHighlight == null) {
                syntaxHighlight = new SyntaxHighlightNoOp();
            }

            if (linkResolver == null) {
                linkResolver = new LinkResolverDef();
            }

            if (urlProcessor == null) {
                urlProcessor = new UrlProcessorNoOp();
            }

            if (imageSizeResolver == null) {
                imageSizeResolver = new ImageSizeResolverDef();
            }

            // @since 1.1.0
            if (factory == null) {
                factory = SpannableFactoryDef.create();
            }

            // @since 2.0.0
            if (htmlParser == null) {
                htmlParser = MarkwonHtmlParser.noOp();
            }

            // @since 2.0.0
            if (htmlRenderer == null) {
                htmlRenderer = MarkwonHtmlRenderer.noOp();
            }

            return new MarkwonConfiguration(this);
        }
    }

}
