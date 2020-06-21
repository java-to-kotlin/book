
formats=pdf html

srcroot = text/src/book.ad
srcdir := $(dir $(srcroot))
src := $(shell find $(srcdir) -not -path '*/.*' -print)

parts := $(patsubst %-part,%,$(notdir $(wildcard $(srcdir)/*-part)))

outdir=$(abspath $(dir $@))
imagesoutdir=$(outdir)/images
outtype=$(subst .,,$(suffix $@))

asciidoctor_opts=\
	--doctype=book \
	--source-dir=book \
	--destination-dir="$(outdir)" \
	--out-file="$(abspath $@)" \
	--require=asciidoctor-diagram \
	--require=asciidoctor-bibtex \
	--attribute source-highlighter=rouge \
	--attribute imagesoutdir="$(imagesoutdir)" \
	$(asciidoctor_opts_$(outtype))

asciidoctor_opts_pdf=--require=asciidoctor-pdf
asciidoctor_opts_html=--attribute imagesdir=images

all: $(formats)
$(foreach f,$(formats),$(eval $f: out/$f/book.$f;))

# The cd $(srcdir) gubbins works around Asciidoctor extensions that do not
# resolve files relative to the root document correctly
out/%: backend=$(outtype)
out/%: $(src) | $(dir $@)/ text/src/style.plantuml
	cd $(srcdir) && asciidoctor $(asciidoctor_opts) --backend=$(backend) $(notdir $(srcroot))


out/pdf/%-part.pdf:
out/pdf/%-part.pdf: $(wildcard %-part/*.ad)
	cd $(dir $(srcroot)) && asciidoctor $(asciidoctor_opts) \
		--attribute=doctype=book \
		--attribute=pdf-page-size=7inx9.1875in \
		--attribute=icons=font \
		--attribute=sectnums \
		--attribute=toc \
		--backend=$(backend) \
		"$*-part/$*.ad"



%/:
	mkdir -p $@

clean:
	rm -rf out/

show-%:
	@echo "$*=$($*)"

.PHONY: all html pdf continually clean show-%

$(foreach p,$(parts),$(eval $p-part: out/pdf/$p-part.pdf))
