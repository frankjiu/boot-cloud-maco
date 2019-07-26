;(function($) {
    $.fn.reveal = function(options) {
        var defaults = {
            animation: 'fadeAndPop',
            animationspeed: 300,
            closeonbackgroundclick: false,
            dismissmodalclass: 'close-reveal-modal'
        };
        var options = $.extend({}, defaults, options);
        return this.each(function() {
            var modal = $(this),
                locked = false,
                modalBG = $('.layer-shade');
            var winl,winh,modalw,modalh,oleft,otop;
            (function () {
                window.onresize = arguments.callee;
                winl = $(window).width();
                winh = $(window).height();
                modalw = modal.width();
                modalh = modal.height();
                oleft = parseInt((winl - modalw)/2),
                otop = parseInt((winh - modalh)/2);
                modal.css({'left':oleft+'px'});
            })();
            if (modalBG.length == 0) {
                modalBG = $('<div class="layer-shade" />').insertAfter(modal);
            }
            modal.bind('reveal:open',
                function() {
                    modalBG.unbind('click.modalEvent');
                    $('.' + options.dismissmodalclass).unbind('click.modalEvent');
                    if (!locked) {
                        lockModal();
                        if (options.animation == "fadeAndPop") {
                            modal.css({
                                'top': $(document).scrollTop() - otop,
                                'opacity': 0,
                                'visibility': 'visible'
                            });
                            modalBG.fadeIn(options.animationspeed / 2);
                            modal.delay(options.animationspeed / 2).animate({
                                    "top": $(document).scrollTop() + otop + 'px',
                                    "opacity": 1
                                },
                                options.animationspeed, unlockModal());
                        }
                        if (options.animation == "fade") {
                            modal.css({
                                'opacity': 0,
                                'visibility': 'visible',
                                'top': $(document).scrollTop() + otop
                            });
                            modalBG.fadeIn(options.animationspeed / 2);
                            modal.delay(options.animationspeed / 2).animate({
                                    "opacity": 1
                                },
                                options.animationspeed, unlockModal());
                        }
                        if (options.animation == "none") {
                            modal.css({
                                'visibility': 'visible',
                                //  'left': $(window).width() + leftMeasure,
                                'top': $(document).scrollTop() + otop
                            });
                            modalBG.css({
                                "display": "block"
                            });
                            unlockModal()
                        }
                    }
                    modal.unbind('reveal:open');
                });
            modal.bind('reveal:close',
                function() {
                    if (!locked) {
                        lockModal();
                        if (options.animation == "fadeAndPop") {
                            modalBG.delay(options.animationspeed).fadeOut(options.animationspeed);
                            modal.animate({
                                    "top": $(document).scrollTop() - otop + 'px',
                                    "opacity": 0
                                },
                                options.animationspeed / 2,
                                function() {
                                    modal.css({
                                        'top': otop + 'px',
                                        'opacity': 1,
                                        'visibility': 'hidden'
                                    });
                                    unlockModal();
                                });
                        }
                        if (options.animation == "fade") {
                            modalBG.delay(options.animationspeed).fadeOut(options.animationspeed);
                            modal.animate({
                                    "opacity": 0
                                },
                                options.animationspeed,
                                function() {
                                    modal.css({
                                        'opacity': 1,
                                        'visibility': 'hidden',
                                        'top': otop + 'px',
                                    });
                                    unlockModal();
                                });
                        }
                        if (options.animation == "none") {
                            modal.css({
                                'visibility': 'hidden',
                                'top': otop + 'px',
                            });
                            modalBG.css({
                                'display': 'none'
                            });
                        }
                    }
                    modal.unbind('reveal:close');
                });
            modal.trigger('reveal:open');
            var closeButton = $('.' + options.dismissmodalclass).bind('click.modalEvent',
                function() {
                    modal.trigger('reveal:close')
                });
            if (options.closeonbackgroundclick) {
                modalBG.bind('click.modalEvent',
                    function() {
                        modal.trigger('reveal:close')
                    });
            }
            $('body').keyup(function(e) {
                if (e.which === 27) {
                    modal.trigger('reveal:close');
                }
            });
            function unlockModal() {
                locked = false;
            }
            function lockModal() {
                locked = true;
            }

        });
    }
})(jQuery);