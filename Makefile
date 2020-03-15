
formats=html pdf epub

src:=$(wildcard text/src/*.ad)
srcroot = text/src/book.ad

asciidoctor_plugins=diagram epub3 pdf

asciidoctor_opts=\
  --doctype=book \
	--source-dir=book --destination-dir=$(dir $@) \
	$(asciidoctor_plugins:%=--require=asciidoctor-%) \
	--attribute source-highlighter=rouge


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
