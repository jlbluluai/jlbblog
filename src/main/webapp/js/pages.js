var $getPages = function() {
	$(".page_item").empty();
	for(var i = totalPage; i > 0; i--) {
		if(i == currentPage) {
			var $a = $("<a>", {
				href: "javascript:void(0);",
				text: i
			});
			var $li = $("<li>", {
				class: "active page_item"
			});
			$li.append($a);
			$("#firstli").after($li);
		} else {
			if((totalPage - currentPage) > 6) {
				if(i == totalPage) {
					var $a1 = $("<a>", {
						href: "javascript:void(0);",
						text: i
					});
					var $li1 = $("<li>", {
						class: "page_item"
					});
					$li1.append($a1);
					$("#firstli").after($li1);
					var $a2 = $("<a>", {
						href: "javascript:void(0);",
						text: "..."
					});
					var $li2 = $("<li>", {
						class: "page_item"
					});
					$li2.append($a2);
					$("#firstli").after($li2);
				}
				if(i < (currentPage + 6) && i > currentPage) {
					var $a1 = $("<a>", {
						href: "javascript:void(0);",
						text: i
					});
					var $li1 = $("<li>", {
						class: "page_item"
					});
					$li1.append($a1);
					$("#firstli").after($li1);
				}
			}
			if((totalPage - currentPage) <= 6) {
				if(i > currentPage) {
					var $a1 = $("<a>", {
						href: "javascript:void(0);",
						text: i
					});
					var $li1 = $("<li>", {
						class: "page_item"
					});
					$li1.append($a1);
					$("#firstli").after($li1);
				}
			}
			if(currentPage > 7) {
				if(i == 1) {
					var $a1 = $("<a>", {
						href: "javascript:void(0);",
						text: i
					});
					var $li1 = $("<li>", {
						class: "page_item"
					});
					$li1.append($a1);
					var $a2 = $("<a>", {
						href: "javascript:void(0);",
						text: "..."
					});
					var $li2 = $("<li>", {
						class: "page_item"
					});
					$li2.append($a2);
					$("#firstli").after($li2);
					$("#firstli").after($li1);
				}
				if(i > (currentPage - 6) && i < currentPage) {
					var $a1 = $("<a>", {
						href: "javascript:void(0);",
						text: i
					});
					var $li1 = $("<li>", {
						class: "page_item"
					});
					$li1.append($a1);
					$("#firstli").after($li1);
				}
			}
			if(currentPage <= 7) {
				if(i < currentPage) {
					var $a1 = $("<a>", {
						href: "javascript:void(0);",
						text: i
					});
					var $li1 = $("<li>", {
						class: "page_item"
					});
					$li1.append($a1);
					$("#firstli").after($li1);
				}
			}
		}
	}
}

