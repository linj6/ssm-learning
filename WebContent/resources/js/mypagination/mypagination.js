+ function($) {

	var PageProxy = function(options, page) {
		this.isCurrent = function() {
			return page == options.currentPage;
		}

		this.isFirst = function() {
			return page == 1;
		}

		this.isLast = function() {
			return page == options.totalPages;
		}

		this.isPrev = function() {
			return page == (options.currentPage - 1);
		}

		this.isNext = function() {
			return page == (options.currentPage + 1);
		}

		this.isLeftOuter = function() {
			return page <= options.outerWindow;
		}


		this.isRightOuter = function() {
			return (options.totalPages - page) < options.outerWindow;
		}

		this.isInsideWindow = function() {
			if (options.currentPage < options.innerWindow + 1) {
				return page <= ((options.innerWindow * 2) + 1);
			} else if (options.currentPage > (options.totalPages - options.innerWindow)) {
				return (options.totalPages - page) <= (options.innerWindow * 2);
			} else {
				return Math.abs(options.currentPage - page) <= options.innerWindow;
			}
		}

		this.number = function() {
			return page;
		}
		this.pageSize = function() {
			return options.pageSize;

		}
	}

	var View = {
		firstPage: function(pagin, options, currentPageProxy) {
			return '<li role="first"' + (currentPageProxy.isFirst() ? 'class="disabled"' : '') + '><a href="#">' + options.first + '</a></li>';
		},

		prevPage: function(pagin, options, currentPageProxy) {
			return '<li role="prev"' + (currentPageProxy.isFirst() ? 'class="disabled"' : '') + '><a href="#" rel="prev">' + options.prev + '</a></li>';
		},

		nextPage: function(pagin, options, currentPageProxy) {
			return '<li role="next"' + (currentPageProxy.isLast() ? 'class="disabled"' : '') + '><a href="#" rel="next">' + options.next + '</a></li>';
		},

		lastPage: function(pagin, options, currentPageProxy) {

			return '<li role="last"' + (currentPageProxy.isLast() ? 'class="disabled"' : '') + '><a href="#">' + options.last + '</a></li>';
		},

		gap: function(pagin, options) {
			return '<li role="gap" class="disabled"><a href="#">' + options.gap + '</a></li>';
		},

		page: function(pagin, options, pageProxy) {
			return '<li role="page"' + (pageProxy.isCurrent() ? 'class="active"' : '') + '><a href="#"' + (pageProxy.isNext() ? ' rel="next"' : '') + (pageProxy.isPrev() ? 'rel="prev"' : '') + '>' + pageProxy.number() + '</a></li>';
		}

	}



	var Pagination = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, Pagination.DEFAULTS, options);

		this.$ul = this.$element; //.find("ul");
		this.render();
	}

	Pagination.DEFAULTS = {
		currentPage: 1,
		totalPages: 1,
		pageSize: 20,
		innerWindow: 2,
		outerWindow: 0,
		first: '&laquo;',
		prev: '&lsaquo;',
		next: '&rsaquo;',
		last: '&raquo;',
		gap: '..',
		totalText: '合计:',
		truncate: false,
		page: function(page) {
			return true
		}
	}

	Pagination.prototype.update = function(options) {
		this.$ul.empty();
		this.options = $.extend({}, this.options, options);
		this.render();
	}
	Pagination.prototype.render = function() {
		var a = (new Date()).valueOf()

		var options = this.options;

		if (!options.totalPages) {
			this.$element.hide();
			return;
		} else {
			this.$element.show();
		}

		var htmlArr = []
		var currentPageProxy = new PageProxy(options, options.currentPage);

		if (!currentPageProxy.isFirst() || !options.truncate) {
			if (options.first) {
				htmlArr.push(View.firstPage(this, options, currentPageProxy))
			}

			if (options.prev) {
				htmlArr.push(View.prevPage(this, options, currentPageProxy));
			}
		}

		var wasTruncated = false;

		for (var i = 1, length = options.totalPages; i <= length; i++) {
			var pageProxy = new PageProxy(options, i);
			if (pageProxy.isLeftOuter() || pageProxy.isRightOuter() || pageProxy.isInsideWindow()) {
				htmlArr.push(View.page(this, options, pageProxy));
				wasTruncated = false;
			} else {
				if (!wasTruncated && options.outerWindow > 0) {
					htmlArr.push(View.gap(this, options));
					wasTruncated = true;
				}
			}
		}

		if (!currentPageProxy.isLast() || !options.truncate) {
			if (options.next) {
				htmlArr.push(View.nextPage(this, options, currentPageProxy));
			}

			if (options.last) {
				htmlArr.push(View.lastPage(this, options, currentPageProxy));
			}
		}

		if (options.totalCount > 0) {
			var htmlStr = '<li><a style="cursor:default;background-color:#FFF;">' + options.totalText + options.totalCount + '</a></li>';
			htmlArr.push(htmlStr);
		}

		if (options.jumppage || options.pageSize) {
			var jumppagehtml = '<input class="page_j" style="margin-right: 6px;width: 32px;border: 1px solid #ddd; height: 20px; padding-left: 2px; border-radius: 3px;" value=' + options.currentPage + '>页&nbsp;&nbsp;'
			var sizehtml = '显示<input  class="page_z"  style="margin:0px 6px;width: 32px;border: 1px solid #ddd; height: 20px; padding-left: 2px; border-radius: 3px;" value=' + options.pageSize + '>条&nbsp;&nbsp;'
			var tmpjump = "<li><a>" + (options.jumppage ? jumppagehtml : "") + (options.pageSize ? sizehtml : "") + "<i class='jump_page fa fa-arrow-circle-right' style='margin-left: 8px; cursor: pointer;'></i></a></li>";
			htmlArr.push(tmpjump)

		}

		this.$ul[0].insertAdjacentHTML('beforeEnd', htmlArr.join(''))

		var me = this;
		$(".jump_page").off("click").on("click", function() {
			var jp, pz;
			jp = $(this).siblings(".page_j").val() ? $(this).siblings(".page_j").val() : options.currentPage;
			pz = $(this).siblings(".page_z").val() ? $(this).siblings(".page_z").val() : options.pageSize;
			me.page(jp, options.totalPages, pz);
			me.$element.trigger('pageChange', jp)
			return false;
		})

		this.$ul.find('[role="first"] > a').bind('click.bs-pagin', function() {
			if (options.currentPage <= 1) return;
			me.$element.trigger('pageChange', 1)
			me.firstPage();

			return false;
		})
		this.$ul.find('[role="prev"] > a').bind('click.bs-pagin', function() {
			if (options.currentPage <= 1) return;
			me.$element.trigger('pageChange', options.currentPage - 1)
			me.prevPage();

			return false;
		})

		this.$ul.find('[role="next"] > a').bind('click.bs-pagin', function() {
			if (options.currentPage + 1 > options.totalPages) return;
			me.$element.trigger('pageChange', options.currentPage + 1)
			me.nextPage();

			return false;
		})

		this.$ul.find('[role="last"] > a').bind('click.bs-pagin', function() {
			if (options.currentPage == options.totalPages) return;
			me.$element.trigger('pageChange', options.totalPages)
			me.lastPage();

			return false;

		})

		this.$ul.find('[role="page"] > a').bind('click.bs-pagin', function() {
			var pz = me.$element.find(".page_z").val() ? me.$element.find(".page_z").val() : options.pageSize;
			me.page(parseInt($(this).html()), options.totalPages, pz);
			me.$element.trigger('pageChange', parseInt($(this).html()))

			return false;
		});
	}


	Pagination.prototype.page = function(page, totalPages, pageSize) {

		var options = this.options;

		if (totalPages === undefined) {
			totalPages = options.totalPages;
		}
		if (pageSize === undefined) {
			pageSize = options.pageSize;
		}

		if (page > 0 && page <= totalPages) {
			if (options.page(page)) {

				this.$ul.empty();
				options.pageSize = pageSize;
				options.currentPage = page;
				options.totalPages = totalPages;
				this.render();

			}
		}

		return false;
	}

	Pagination.prototype.firstPage = function() {
		return this.page(1);
	}

	Pagination.prototype.lastPage = function() {
		return this.page(this.options.totalPages);
	}

	Pagination.prototype.nextPage = function() {
		return this.page(this.options.currentPage + 1);
	}

	Pagination.prototype.prevPage = function() {
		return this.page(this.options.currentPage - 1);
	}


	function Plugin(option) {
		return this.each(function() {
			var $this = $(this)
			var data = $this.data('u.pagination')
			var options = typeof option == 'object' && option

			if (!data) $this.data('u.pagination', (data = new Pagination(this, options)))
			else data.update(options);
		})
	}


	var old = $.fn.pagination;
//	var old = Plugin;

	$.fn.pagination = Plugin
	$.fn.pagination.Constructor = Pagination


	$.fn.pagination.noConflict = function() {
		$.fn.pagination = old;
		return this;
	}

}(jQuery);