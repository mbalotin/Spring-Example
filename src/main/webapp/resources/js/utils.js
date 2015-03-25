//Prevent enter key-press submit
$(function () {
  $("body").keydown(function (e) {
    var evt = (e) ? e : ((event) ? event : null);
    var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
    if ((evt.keyCode == 13) && ((node.type == "text") || (node.type == "textarea")) && !$(node).hasClass('ui-column-filter')) {
      e.preventDefault();
    }
  });
})
