
formats=html pdf

src:=$(wildcard text/src/*.ad) $(wildcard text/src/diagrams/*.plantuml)
srcroot = text/src/book.ad

outdir=$(abspath $(dir $@))
imagesoutdir=$(outdir)/images
outtype=$(subst .,,$(suffix $@))

asciidoctor_opts=\
	--doctype=book \
	--source-dir=book \
	--destination-dir="$(outdir)" \
	--require=asciidoctor-diagram \
	--attribute source-highlighter=rouge \
	--attribute imagesoutdir="$(imagesoutdir)" \
	$(asciidoctor_opts_$(outtype))

asciidoctor_opts_pdf=--require=asciidoctor-pdf --attribute imagesdir="$(imagesoutdir)"
asciidoctor_opts_html=--attribute imagesdir=images

all: $(formats)
$(foreach f,$(formats),$(eval $f: out/$f/book.$f;))

out/%: backend=$(outtype)
out/%: $(src) | $(dir $@)/
	asciidoctor $(asciidoctor_opts) --backend=$(backend) $(srcroot)

%/:
	mkdir -p $@

clean:
	rm -rf out/

.PHONY: all html pdf continually clean
