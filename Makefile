
formats=pdf html

srcroot = text/src/book.ad
src:=$(shell find $(dir $(srcroot)) -not -path '*/.*' -print)

outdir=$(abspath $(dir $@))
imagesoutdir=$(outdir)/images
outtype=$(subst .,,$(suffix $@))

asciidoctor_opts=\
	--doctype=book \
	--source-dir=book \
	--destination-dir="$(outdir)" \
	--require=asciidoctor-diagram \
	--require=asciidoctor-bibtex \
	--attribute source-highlighter=rouge \
	--attribute imagesoutdir="$(imagesoutdir)" \
	$(asciidoctor_opts_$(outtype))

asciidoctor_opts_pdf=--require=asciidoctor-pdf
asciidoctor_opts_html=--attribute imagesdir=images

all: $(formats)
$(foreach f,$(formats),$(eval $f: out/$f/book.$f;))

# The cd $(dir $(srcroot)) gubbins works around Asciidoctor extensoins that do not
# resolve files relative to the root document correctly
out/%: backend=$(outtype)
out/%: $(src) | $(dir $@)/ text/src/style.plantuml
	cd $(dir $(srcroot)) && asciidoctor $(asciidoctor_opts) --backend=$(backend) $(notdir $(srcroot))

%/:
	mkdir -p $@

clean:
	rm -rf out/

.PHONY: all html pdf continually clean
