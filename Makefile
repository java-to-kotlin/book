
formats=html pdf epub

src:=$(wildcard text/src/*.ad) $(wildcard text/src/diagrams/*.plantuml)
srcroot = text/src/book.ad

asciidoctor_plugins=diagram epub3 pdf

outdir=$(abspath $(dir $@))
imagesoutdir=$(outdir)/images

asciidoctor_opts=\
	--doctype=book \
	--source-dir=book \
	--destination-dir="$(outdir)" \
	$(asciidoctor_plugins:%=--require=asciidoctor-%) \
	--attribute source-highlighter=rouge \
	--attribute imagesoutdir="$(imagesoutdir)" \
	--attribute imagesdir="$(imagesoutdir)" \
	$(asciidoctor_opts_$(backend))

# embed images in HTML docs to work around asciidoctor's appalling handling of image paths
asciidoctor_opts_html=--attribute data-uri

all: $(formats)
$(foreach f,$(formats),$(eval $f: out/$f/book.$f;))

out/%: backend=$(subst .,,$(suffix $@))
out/%.epub: backend=epub3
out/%: $(src) | $(dir $@)/
	asciidoctor $(asciidoctor_opts) --backend=$(backend) $(srcroot)

%/:
	mkdir -p $@

clean:
	rm -rf out/

.PHONY: all html pdf continually clean
